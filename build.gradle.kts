plugins {
    id("fabric-loom").version("0.10.65").apply(false)
}

fun isMainSubProject(it: Project) = it.name == "fabric" || it.name == "forge"

fun isFabricProject(it: Project) = "fabric" in it.name

val javaVersion = JavaVersion.VERSION_17
val jetbrainsAnnotationsVersion = "23.0.0"
val minecraftVersion = "1.18.1"
val yarnVersion = "12"

subprojects {
    apply(plugin = "java-library")

    val javaPluginExtension = project.extensions.getByType(JavaPluginExtension::class)

    group = "ninjaphenix"
    version = "${properties["mod_version"]}+${minecraftVersion}"
    project.extensions.getByType(BasePluginExtension::class).apply {
        archivesName.set("${properties["archives_base_name"]}")
    }
    buildDir = rootDir.resolve("build/${project.name}")

    project.extensions.getByType(JavaPluginExtension::class).apply {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }

    dependencies {
        add("implementation", "org.jetbrains:annotations:${jetbrainsAnnotationsVersion}")
    }

    if (isFabricProject(project)) {
        apply(plugin = "fabric-loom")

        if (isMainSubProject(project)) {
            javaPluginExtension.sourceSets {
                named("main") {
                    resources {
                        srcDir("src/main/generated")
                    }
                }

                create("datagen") {
                    compileClasspath += named("main").get().compileClasspath
                    runtimeClasspath += named("main").get().runtimeClasspath
                    compileClasspath += named("main").get().output
                    runtimeClasspath += named("main").get().output
                }
            }
        }

        dependencies {
            add("minecraft", "com.mojang:minecraft:${minecraftVersion}")
            add("mappings", "net.fabricmc:yarn:${minecraftVersion}+build.${yarnVersion}:v2")

            add("modImplementation", "net.fabricmc:fabric-loader:${properties["fabric_loader_version"]}")
            add("modImplementation", "net.fabricmc.fabric-api:fabric-api:${properties["fabric_api_version"]}")
        }

        project.extensions.getByType(net.fabricmc.loom.api.LoomGradleExtensionAPI::class).apply {
            runs {
                named("client") {
                    ideConfigGenerated(false)
                }
                named("server") {
                    ideConfigGenerated(false)
                    serverWithGui()
                }

                if (isMainSubProject(project)) {
                    create("datagen") {
                        client()
                        vmArg("-Dfabric-api.datagen")
                        vmArg("-Dfabric-api.datagen.output-dir=${file("src/main/generated")}")
                        vmArg("-Dfabric-api.datagen.datagen.modid=${properties["mod_id"]}")
                        runDir("build/fabric-datagen")
                        source(javaPluginExtension.sourceSets.getByName("datagen"))
                    }
                }
            }

            mixin {
                useLegacyMixinAp.set(false)
            }

            project.findProperty("access_widener_path").also {
                accessWidenerPath.set(file("${it}"))
            }
        }

        tasks.withType<ProcessResources> {
            val props = mutableMapOf("version" to properties["mod_version"]) // Needs to be mutable
            inputs.properties(props)
            filesMatching("fabric.mod.json") {
                expand(props)
            }
            exclude(".cache/*")
        }
    }
}

tasks.register("buildMod") {
    subprojects.forEach {
        if (isMainSubProject(it)) {
            dependsOn(it.tasks["build"])
        }
    }
}

tasks.register("sample") {
    println("Running sample code:")
    subprojects.forEach {
        println(it.name)
        println(isMainSubProject(it))
    }
}

// Template above, do not edit.
