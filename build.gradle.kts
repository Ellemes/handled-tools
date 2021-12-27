plugins {
    id("fabric-loom").version("0.10.65").apply(false)
}

fun isMainSubProject(name: String): Boolean {
    return name == "fabric" || name == "forge"
}

subprojects {
    apply(plugin = "java-library")

    group = "ninjaphenix"
    version = "${properties["mod_version"]}+${properties["minecraft_version"]}"
    project.extensions.getByType(BasePluginExtension::class).apply {
        archivesName.set(properties["archives_base_name"] as String)
    }
    buildDir = rootDir.resolve("build/${project.name}")

    project.extensions.getByType(JavaPluginExtension::class).apply {
        val javaVersion = JavaVersion.VERSION_17
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }
}

tasks.register("buildMod") {
    subprojects.forEach {
        if (isMainSubProject(it.name)) {
            dependsOn(it.tasks["build"])
        }
    }
}

// Template above, do not edit.
