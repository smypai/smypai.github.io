1. idea 工程根目录创建lib文件夹，放入jar包
2. build.gradle 通过dependencies引入
```gradle
dependencies {
    // 依赖lib目录下的某个jar文件
    implementation files('lib/xxx.jar')


    // 依赖lib目录下的所有以.jar结尾的文件
    implementation fileTree(dir: 'lib', includes: ['*.jar'])


    // 依赖lib目录下的除了xxx.jar以外的所有以.jar结尾的文件
    implementation fileTree(dir: 'lib', excludes: ['xxx.jar'], includes: ['*.jar'])
}
```
3.  build.gradle 通过repositories引用
```gradle
repositories {
	flatDir(dirs: "lib")
    mavenCentral()
}
```