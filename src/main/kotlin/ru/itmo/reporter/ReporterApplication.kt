package ru.itmo.reporter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator

@SpringBootApplication
@ComponentScan(
    basePackages = ["ru.itmo.reporter"],
    nameGenerator = FullyQualifiedAnnotationBeanNameGenerator::class
)
class ReporterApplication

fun main(args: Array<String>) {
    runApplication<ReporterApplication>(*args)
}
