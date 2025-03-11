plugins {
    alias(libs.plugins.kotlinMultiplatform)
    `java-test-fixtures`
}

kotlin {
    jvmToolchain(17)

    jvm("desktop")
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlin("test"))
                api(libs.kotlinx.coroutines.test)
                api(libs.turbine)
                api(libs.mockk)
                api(libs.kotlin.test.junit)
                implementation(project(":common:models"))
            }
        }
    }
}



