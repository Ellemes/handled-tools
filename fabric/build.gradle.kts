plugins {
    java
    id("fabric-loom")
}

sourceSets {
    main {
        resources {
            srcDir("src/main/generated")
        }
    }

    create("datagen") {
        compileClasspath += sourceSets.main.get().compileClasspath
        runtimeClasspath += sourceSets.main.get().runtimeClasspath
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

loom {
    runs {
        named("client") {
            ideConfigGenerated(false)
        }
        named("server") {
            ideConfigGenerated(false)
            serverWithGui()
        }
        create("datagen") {
            client()
            vmArg("-Dfabric-api.datagen")
            vmArg("-Dfabric-api.datagen.output-dir=${file("src/main/generated")}")
            vmArg("-Dfabric-api.datagen.datagen.modid=${properties["mod_id"]}")
            runDir("build/fabric-datagen")
            source(sourceSets.getByName("datagen"))
        }
    }

    mixin {
        useLegacyMixinAp.set(false)
    }

    if (project.hasProperty("access_widener_path")) {
        accessWidenerPath.set(file(properties["access_widener_path"] as String))
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${properties["minecraft_version"]}")
    mappings("net.fabricmc:yarn:${properties["minecraft_version"]}+build.${properties["yarn_version"]}:v2")

    modImplementation("net.fabricmc:fabric-loader:${properties["fabric_loader_version"]}")
    implementation("org.jetbrains:annotations:${properties["jetbrains_annotations_version"]}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${properties["fabric_api_version"]}")
}

tasks.withType<ProcessResources> {
    val props = mutableMapOf("version" to properties["mod_version"]) // Needs to be mutable
    inputs.properties(props)
    filesMatching("fabric.mod.json") {
        expand(props)
    }
    exclude(".cache/*")
}

// Template above, do not edit.