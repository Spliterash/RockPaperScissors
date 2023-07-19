plugins {
    id("org.springframework.boot") version "3.1.1"
}

allprojects {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
    }
}

val coreProject = project("core")

// Всё суб проекты имеют доступ к core
(subprojects - coreProject).forEach { sub ->
    sub.dependencies.api(coreProject)
}

// Главный проект имеет в себе все суб проекты
subprojects.forEach { sub ->
    dependencies.implementation(sub)
}


val copyJar = tasks.register<Copy>("copyJar") {
    dependsOn(tasks.bootJar) // Указываем, что задача копирования зависит от задачи "jar"
    from(tasks.bootJar.get().archiveFile) // Определяем исходный JAR-файл
    into(rootProject.file("docker/jars")) // Указываем путь к целевой папке, куда будет скопирован JAR-файл
    rename { "server.jar" }
}
tasks.assemble { dependsOn(copyJar) }