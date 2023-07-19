plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
}

application {
    mainClass.set("ru.spliterash.rps.client.RockPaperScissorsClient")
}

dependencies {
    implementation(project(":common:kryo"))
}


val copyJar = tasks.register<Copy>("copyJar") {
    dependsOn(tasks.shadowJar) // Указываем, что задача копирования зависит от задачи "jar"
    from(tasks.shadowJar.get().archiveFile) // Определяем исходный JAR-файл
    into(rootProject.file("docker/jars")) // Указываем путь к целевой папке, куда будет скопирован JAR-файл
    rename { "client.jar" }
}
tasks.assemble { dependsOn(copyJar) }