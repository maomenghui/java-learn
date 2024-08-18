CloudCrypto 项目

CloudCrypto 是一个为云存储/计算应用实现加密原语的项目。
项目简介

传统的公钥加密原语（如 Diffie-Hellman、RSA、ElGamal）已广泛应用于保护网络和存储系统。然而，这些方案的功能有限。为满足云存储/计算环境的功能和安全需求，研究者提出了高级加密原语和方案。

CloudCrypto 项目旨在在 Java 加密架构（JCA）下实现这些高级加密方案。为了达到这一目标，CloudCrypto 使用 Bouncy Castle 作为底层库，该库严格遵循 JCA 标准。尽管 Java 在运行效率上不理想，但它具有良好的可移植性，使 CloudCrypto 可以直接移植到移动设备或嵌入式设备（如 Android）上。

CloudCrypto 源代码免费开放，供研究与开发使用。我们很高兴看到 Medica Corp. 正在实验性地将 CloudCrypto 应用于研究与开发中。
开发环境

CloudCrypto 使用 Maven 2 构建。请参考 pom.xml 获取依赖库。当前版本的 CloudCrypto 依赖以下库：

    Bouncy Castle: 支持基础加密原语，如哈希函数和对称加密方案。
    jPBC Library: 支持双线性群。
    JUnit: 用于单元测试。
    普林斯顿大学的标准输入输出库: 来自普林斯顿开放课程《编程入门：跨学科方法》的易用标准输入输出库。我们只使用了其中的 In.java、Out.java、StdIn.java、StdOut.java、BinaryIn.java 和 BinaryOut.java，并将这些文件直接包含在项目中，代码位于 /src/main/java/edu/princeton/cs/algs4。

基于双线性群的加密原语

当前版本的 CloudCrypto 主要专注于基于双线性群的方案实现。底层代数库使用的是 Java Pairing-Based Cryptography Library。
挑战

现有的 Java 实现的双线性群加密方案存在以下问题：

    缺少有效的序列化方法。
    加密方案加密的是 G_T 群中的随机元素，而没有合理的方法将消息/明文一一映射到 G_T 中的元素，导致解密后无法将 G_T 中的元素还原为原始消息。

解决方案

我们利用 Java 内置的序列化方法，支持将任意 CipherParameters 对象序列化为字节数组，从而支持用户将生成的 CipherParameters 上传至云端。所有名称以 SerParameter 结尾的对象均支持序列化。以下是如何在 src/main/java/cn/edu/buaa/crypto/utils/PairingUtils.java 中进行序列化/反序列化：

java

public static byte[] SerCipherParameter(CipherParameters cipherParameters) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
    objectOutputStream.writeObject(cipherParameters);
    byte[] byteArray = byteArrayOutputStream.toByteArray();
    objectOutputStream.close();
    byteArrayOutputStream.close();
    return byteArray;
}

我们还提供了密钥封装机制，用于加密方案，即加密算法封装一个随机会话密钥，然后加密者可以使用该密钥通过对称加密方案（如 AES、TwoFish）加密任意数据。
PairingParameters 对象

双线性群加密方案中最重要的对象是 PairingParameters，它属于 jPBC 库，提供了双线性群所需的全部信息。生成这些参数较为复杂，尤其是在不了解双线性群背景的情况下。我们预生成了一些参数，存储在 /params 目录下，用于单元测试的参数包括：

    a_80_256.properties：Type A 素数阶双线性群，80 位 Z_p 和 256 位 G。此群阶不满足现代系统的安全需求，仅用于测试方案实现的正确性。
    a_160_512.properties：Type A 素数阶双线性群，160 位 Z_p 和 512 位 G。
    a1_3_128.properties：Type A1 复合阶双线性群，包含 3 个 128 位的素数因子。不满足现代系统的安全需求，仅用于测试。
    a1_3_512.properties：Type A1 复合阶双线性群，包含 3 个 512 位的素数因子。
    f_160.properties：Type F 素数阶双线性群，160 位 Z_p。该群是非对称双线性群，仅用于基于非对称双线性群的方案，推荐用于 Boneh-Lynn-Shacham 短签名方案。

以下代码展示了如何从文件中获取 PairingParameters：

java

// 从 /params/a_160_512.properties 获取 PairingParameters
PairingParameters pairingParameters = PairingFactory.getPairingParameters("params/a_160_512.properties");

代数算法
线性秘密共享方案 (LSSS)

线性秘密共享方案 (LSSS) 是 Shamir 秘密共享方案的推广。Shamir 的方案是 Shamir 在 1979 年提出的一种经典加密原语 (参见论文 How To Share a Secret)。1996 年，Beimel 引入了 LSSS 的概念，表明 Shamir 的方案可以视为 LSSS 的一种特殊情况 (参见论文 Secret Schemes for Secret Sharing and Key Distribution)。如今，LSSS 已成为构建属性基加密 (ABE) 系统中访问控制机制的基础原语。

我们实现了基于 LSSS 的两种访问控制机制，一种基于 Shamir 的原始方案，另一种基于 Waters 和 Lewko 的构建 (参见论文 Decentralizing Attribute-Based Encryption 附录 G)。

以下代码展示了如何使用 LSSS 构建和使用访问控制机制，访问树由 int[][] 表示，访问树的表示方法可参考 源代码 中的注释：

java

Pairing pairing = PairingFactory.getPairing(pairingParameters);
// 注意：Lewko-Waters LSSS 不支持阈值门访问控制。
int[][] accessPolicy = {
        {2,2,1,2},
        {2,2,3,4},
        {4,3,-7,-8,-9,-10},
        {2,2,-2,5},
        {3,2,-4,-5,-6},
        {2,1,-1,-3}
};
// rhos 可以是任意字符串
String[] rhos = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
String[] satisfiedRhos = new String[] {"2", "3", "5", "6", "7", "9", "10"};
// 使用访问树
AccessControlEngine accessControlEngine = AccessTreeEngine.getInstance();
try {
    AccessControlParameter accessControlParameter = accessControlEngine.generateAccessControl(accessPolicy, rhos);
    // 秘密共享
    Element secret = pairing.getZr().newRandomElement().getImmutable();
    Map<String, Element> lambdaElementsMap = accessControlEngine.secretSharing(pairing, secret, accessControlParameter);

    // 秘密重构
    Map<String, Element> omegaElementsMap = accessControlEngine.reconstructOmegas(pairing, satisfiedRhos, accessControlParameter);
    Element reconstructedSecret = pairing.getZr().newZeroElement().getImmutable();
    for (String eachAttribute : satisfiedRhos) {
        if (omegaElementsMap.containsKey(eachAttribute)) {
            reconstructedSecret = reconstructedSecret.add(lambdaElementsMap.get(eachAttribute).mulZn(omegaElementsMap.get(eachAttribute))).getImmutable();
        }
    }
    Assert.assertEquals(secret, reconstructedSecret);
} catch (UnsatisfiedAccessControlException e) {
    // 如果给定的属性集不满足由访问树表示的访问策略，则抛出异常。
}

以下代码展示了如何使用 LSSS 构建和使用访问控制机制，其中访问策略以字符串表示：

java

Pairing pairing = PairingFactory.getPairing(pairingParameters);
AccessControlEngine accessControlEngine = PolicySyntaxTreeEngine.getInstance();
try {
    String accessPolicy = "((1 AND 2) OR (3 AND 4 AND 5 AND (6 OR 7)))";
代码示例展示了如何使用LSSS（线性秘密共享方案）构建和使用访问控制机制，其中访问策略由字符串表示。

java

Pairing pairing = PairingFactory.getPairing(pairingParameters);
String accessPolicyString = "((0 and 1 and 2) and (3 or 4 or 5) and (6 and 7 and (8 or 9 or 10 or 11)))";
String[] satisfiedRhos = new String[] {"0", "1", "2", "4", "6", "7", "10"};
// 使用Lewko-Waters LSSS引擎
AccessControlEngine accessControlEngine = LSSSLW10Engine.getInstance();
try {
    // 解析访问策略
    int[][] accessPolicy = ParserUtils.GenerateAccessPolicy(accessPolicyString);
    String[] rhos = ParserUtils.GenerateRhos(accessPolicyString);
    AccessControlParameter accessControlParameter = accessControlEngine.generateAccessControl(accessPolicy, rhos);
    // 秘密共享
    Element secret = pairing.getZr().newRandomElement().getImmutable();
    Map<String, Element> lambdaElementsMap = accessControlEngine.secretSharing(pairing, secret, accessControlParameter);

    // 秘密重构
    Map<String, Element> omegaElementsMap = accessControlEngine.reconstructOmegas(pairing, satisfiedRhos, accessControlParameter);
    Element reconstructedSecret = pairing.getZr().newZeroElement().getImmutable();
    for (String eachAttribute : satisfiedRhos) {
        if (omegaElementsMap.containsKey(eachAttribute)) {
            reconstructedSecret = reconstructedSecret.add(lambdaElementsMap.get(eachAttribute).mulZn(omegaElementsMap.get(eachAttribute))).getImmutable();
        }
    }
    Assert.assertEquals(secret, reconstructedSecret);
} catch (UnsatisfiedAccessControlException e) {
    // 当给定的属性集不满足由访问树表示的访问策略时，抛出异常。
} catch (PolicySyntaxException e) {
    // 当访问策略表示不合法时，抛出异常。
}

代码讲解与注释

    初始化配对参数：

    java

Pairing pairing = PairingFactory.getPairing(pairingParameters);

配对对象在基于双线性映射的密码系统中用于处理复杂的数学运算。

定义访问策略字符串：

java

String accessPolicyString = "((0 and 1 and 2) and (3 or 4 or 5) and (6 and 7 and (8 or 9 or 10 or 11)))";

此字符串代表了一个LSSS访问策略，使用逻辑表达式描述属性之间的关系。

定义满足策略的属性集合：

java

String[] satisfiedRhos = new String[] {"0", "1", "2", "4", "6", "7", "10"};

这是用于秘密重构的属性集合，表示这些属性已被用户持有。

使用LSSS引擎：

java

AccessControlEngine accessControlEngine = LSSSLW10Engine.getInstance();

此处使用Lewko-Waters的LSSS实现来解析和处理访问控制。

解析访问策略：

java

int[][] accessPolicy = ParserUtils.GenerateAccessPolicy(accessPolicyString);
String[] rhos = ParserUtils.GenerateRhos(accessPolicyString);

访问策略字符串解析为矩阵形式，rhos数组对应属性名称。

秘密共享过程：

java

Element secret = pairing.getZr().newRandomElement().getImmutable();
Map<String, Element> lambdaElementsMap = accessControlEngine.secretSharing(pairing, secret, accessControlParameter);

秘密在属性之间进行分配，每个属性对应一个共享部分。

秘密重构过程：

java

Map<String, Element> omegaElementsMap = accessControlEngine.reconstructOmegas(pairing, satisfiedRhos, accessControlParameter);

根据用户持有的属性，重构出原始秘密。

验证重构的秘密是否与原始秘密一致：

java

    Assert.assertEquals(secret, reconstructedSecret);

    如果一致，说明重构成功，访问策略被正确满足。

    异常处理：
        UnsatisfiedAccessControlException：属性不满足访问策略时抛出。
        PolicySyntaxException：访问策略格式错误时抛出。

该代码实现了LSSS机制下的访问控制，通过属性和策略的匹配，实现秘密的安全共享与重构。