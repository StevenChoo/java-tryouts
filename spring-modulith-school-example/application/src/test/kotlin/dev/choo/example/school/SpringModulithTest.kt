package dev.choo.example.school

import org.junit.jupiter.api.Test
import org.springframework.modulith.core.ApplicationModules
import org.springframework.modulith.docs.Documenter
import kotlin.collections.forEach
import kotlin.io.println
import kotlin.jvm.java

class SpringModulithTest {

    private val modules = ApplicationModules.of(SchoolApplication::class.java)

    @Test
    fun `given proper separation of modules when coding, then expect no violations`() {
        modules.verify()
    }

    @Test
    fun `generate modules and uml`() {
        modules.forEach(::println)

        Documenter(modules)
            .writeDocumentation()
            .writeModuleCanvases()
            .writeModulesAsPlantUml()
            .writeIndividualModulesAsPlantUml()
    }
}
