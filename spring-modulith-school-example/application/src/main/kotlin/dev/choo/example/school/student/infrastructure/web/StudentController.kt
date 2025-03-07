/*
 * Copyright (C) 2025 Steven Choo <info@stevenchoo.nl>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.choo.example.school.student.infrastructure.web

import dev.choo.example.school.student.application.model.RegisterResult
import dev.choo.example.school.student.application.model.UnregisterResult
import dev.choo.example.school.student.application.service.StudentAdministrationService
import dev.choo.example.school.student.application.service.StudentEnrollmentService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/student")
class StudentController(
    private val studentEnrollmentService: StudentEnrollmentService,
    private val studentAdministrationService: StudentAdministrationService,
) {
    @PostMapping("/register")
    fun registerStudent(
        @RequestBody request: StudentRegistrationRequest,
    ): ResponseEntity<StudentIdResponse> {
        val result =
            studentEnrollmentService.registerStudent(
                firstName = request.firstName,
                lastName = request.lastName,
                dateOfBirth = request.dateOfBirth,
            )

        return when (result) { // TODO add proper result handling later
            is RegisterResult.Success ->
                ResponseEntity
                    .status(CREATED)
                    .body(StudentIdResponse.fromDomain(result.studentId))

            is RegisterResult.Failure ->
                throw IllegalStateException(result.reason)
        }
    }

    @PostMapping("/unregister/{studentId}")
    fun unregisterStudent(
        @PathVariable studentId: UUID,
    ): ResponseEntity<Void> {
        val result = studentEnrollmentService.unregisterStudent(studentId)

        return when (result) { // TODO add proper result handing later
            is UnregisterResult.Success -> ResponseEntity.noContent().build()
            is UnregisterResult.Failure -> ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/")
    fun getStudents(): ResponseEntity<List<StudentResponse>> =
        ResponseEntity.ok(studentAdministrationService.getAllStudents().map { StudentResponse.fromDomain(it) })

    @GetMapping("/{studentId}")
    fun getStudent(
        @PathVariable studentId: UUID,
    ): ResponseEntity<StudentResponse> =
        studentAdministrationService.getStudent(studentId)?.let { ResponseEntity.ok(StudentResponse.fromDomain(it)) }
            ?: ResponseEntity.notFound().build()
}
