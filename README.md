


<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/NazarbekAld/SurvivalCore">
    <img src="logo.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">SurvivalCore</h3>

  <p align="center">
    Best API for your development!
    <br />
    <a href="https://github.com/NazarbekAld/SurvivalCore/issues">Report Bug</a>
    ·
    <a href="https://github.com/NazarbekAld/SurvivalCore/issues">Request Feature</a>
    ·
    <a href="https://github.com/NazarbekAld/SurvivalEconomy">EconomyAPI example</a>
  </p>
</div>

<div>

<div align="center">
  <img alt="GitHub contributors" src="https://img.shields.io/github/contributors/NazarbekAld/SurvivalCore?style=for-the-badge">
  <img alt="GitHub repo size" src="https://img.shields.io/github/repo-size/NazarbekAld/SurvivalCore?style=for-the-badge">
  <img alt="GitHub issues" src="https://img.shields.io/github/issues-raw/NazarbekAld/SurvivalCore?style=for-the-badge">
  <img alt="GitHub all releases" src="https://img.shields.io/github/downloads/NazarbekAld/SurvivalCore/total?style=for-the-badge">
</div>

<div align="center">
  <br />
  <a href="https://github.com/PowerNukkitX" target="_blank"> <img alt="PoweredNukkitX", src="https://img.shields.io/badge/PowerNukkitX-Fast open source server framework-lightgrey?style=for-the-badge"> <a/>
  <br />
  <a href="https://maven.apache.org/" target="_blank"><img alt="maven", src="https://img.shields.io/badge/Maven-Dependency manager-yellow?style=for-the-badge"> </a>
  <br />
  <a href="https://projectlombok.org/" target="_blank"> <img alt="lombok", src="https://img.shields.io/badge/Lombok-Generating getters, setters, etc.-red?style=for-the-badge"> <a/>
</div>



## Getting Started


### Installation

* Get [SQLite Libary](https://github.com/xerial/sqlite-jdbc) or MySql driver. Then put in to "libs" folder.
* Get needed version of [SurvivalCore](https://github.com/NazarbekAld/SurvivalCore/releases) and put in to "plugins" folder.


### Dependency

```html
    <repositories>

        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>

    </repositories>

    <dependencies>
        <dependency>
            <groupId>com.github.NazarbekAld</groupId>
            <artifactId>SurvivalCore</artifactId>
            <version>0.1.4</version>
        </dependency>
    </dependencies>
```

### API hook
```java
    // NOTE: You may add null checks!
    EconomyManager economyManager = getServer().getServiceManager().getProvider(EconomyManager.class).getProvider(); // Getting economy manager
    ChatManager chatManager = getServer().getServiceManager().getProvider(ChatManager.class).getProvider(); // Getting chat manager
```
    
    
## Authors

* **NazarbekAlda** - *Initial work* - [NazarbekAld](https://github.com/NazarbekAld)

See also the list of [contributors](https://github.com/NazarbekAld/SurvivalCore/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

