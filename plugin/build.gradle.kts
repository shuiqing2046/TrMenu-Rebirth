val taboolibVersion: String by rootProject

plugins {
    id("io.izzel.taboolib")
}

taboolib {
    install(
        "common",
        "common-5",
        "expansion-javascript",
        "module-kether",
        "module-ui",
        "module-lang",
        "module-database",
        "module-database-mongodb",
        "module-metrics",
        "module-nms",
        "module-chat",
        "module-nms-util",
        "module-configuration",
        "platform-bukkit"
    )

    description {
        name(rootProject.name)
        contributors {
            name("Arasple")
            name("Score2")
        }
        dependencies {
            name("PlaceholderAPI").optional(true)
            name("Skulls").optional(true)
            name("Vault").optional(true)
            name("PlayerPoints").optional(true)
            name("HeadDatabase").optional(true)
            name("SkinsRestorer").optional(true)
            name("ItemsAdder").optional(true)
            name("floodgate-bukkit").optional(true)
            name("FastScript").optional(true)
            name("Triton").optional(true)
        }
    }
    relocate("trplugins.menu", group.toString().toLowerCase())

    classifier = null
    version = taboolibVersion
}

repositories {
    mavenCentral()
    maven("https://repo.tabooproject.org/repository/releases")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.codemc.org/repository/maven-public")
    maven("https://repo.rosewooddev.io/repository/public/")
    maven("https://repo.opencollab.dev/maven-snapshots/")
    maven("https://jitpack.io")
}

dependencies {
    taboo(project(":common"))
    taboo(project(":api:receptacle"))
    taboo(project(":api:action"))

    // Libraries
    compileOnly("org.apache.commons:commons-lang3:3.13.0")
    compileOnly("com.electronwill.night-config:core:3.6.7")

    // Server Core
    compileOnly("ink.ptms.core:v12002:12002-minimize:mapped")
    compileOnly("ink.ptms.core:v12002:12002-minimize:universal")
    compileOnly("ink.ptms.core:v11903:11903-minimize:mapped")
    compileOnly("ink.ptms.core:v11903:11903-minimize:universal")
    compileOnly("ink.ptms.core:v11701:11701-minimize:mapped")
    compileOnly("ink.ptms.core:v11701:11701-minimize:universal")
    compileOnly("ink.ptms.core:v11604:11604")

    // Hook Plugins
    compileOnly("me.clip:placeholderapi:2.11.4") { isTransitive = false }
    compileOnly("ink.ptms:Zaphkiel:2.0.10") { isTransitive = false }
    compileOnly("ca.tweetzy:skulls:3.10.0") { isTransitive = false }
    compileOnly("net.skinsrestorer:skinsrestorer-api:15.0.3") { isTransitive = false }
    compileOnly("org.black_ixx:playerpoints:3.2.6") { isTransitive = false }
    compileOnly("com.github.MilkBowl:VaultAPI:-SNAPSHOT") { isTransitive = false }
    compileOnly("com.github.tritonmc.Triton:core:v3.9.0") { isTransitive = false }
    compileOnly("com.github.tritonmc.Triton:api:v3.9.0") { isTransitive = false }

    compileOnly(fileTree("libs"))
}
