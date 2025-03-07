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

package dev.choo.example.school.student.application.service

import dev.choo.example.school.student.application.model.RegisterResult
import dev.choo.example.school.student.application.model.UnregisterResult
import dev.choo.example.school.student.domain.model.Student
import dev.choo.example.school.student.domain.model.StudentStatus
import dev.choo.example.school.student.domain.repository.StudentRepository
import dev.choo.example.school.student.domain.service.StudentService
import dev.choo.example.school.student.infrastructure.events.StudentEventPublisher
import dev.choo.example.school.student.infrastructure.events.StudentRegisteredEvent
import dev.choo.example.school.student.infrastructure.events.StudentUnRegisteredEvent
import dev.choo.example.school.student.infrastructure.events.StudentUpdatedFailedEvent
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.UUID

/**
 * Application Service that orchestrates enrollment operations.
 * - Coordinates between domain objects, repositories, and domain services
 * - Contains workflow and transaction management
 * - May depend on external systems or infrastructure
 * - Serves as entry point for UI/API
 */
@Service
@Transactional // By default, we want all operations transactional within a Service
class StudentEnrollmentService(
    private val studentRepository: StudentRepository,
    private val studentService: StudentService,
    private val eventPublisher: StudentEventPublisher, // External dependency
) {
    fun registerStudent(
        firstName: String,
        lastName: String,
        dateOfBirth: Instant,
    ): RegisterResult {
        // Create domain object
        val student = Student.register(firstName, lastName, dateOfBirth)

        // Use domain service for business rules
        if (!studentService.isEligibleForRegularEnrollment(student)) {
            return RegisterResult.Failure("Student does not meet age requirements for enrollment")
        }

        // Check for duplicate (could be in domain service too, but often in application layer)
        if (studentRepository.exists(student.id)) {
            return RegisterResult.Failure("Student already exists")
        }

        // Save via repository
        val savedStudent = studentRepository.save(student)

        // Handle side effects - external systems, notifications, etc.
        eventPublisher.publish(StudentRegisteredEvent(savedStudent.id))

        // Return result with appropriate data
        return RegisterResult.Success(
            studentId = savedStudent.id,
            isEligibleForAdvancedPlacement = studentService.isEligibleForAdvancedPlacement(savedStudent),
        )
    }

    fun unregisterStudent(studentId: UUID): UnregisterResult {
        val student =
            studentRepository.findById(studentId)
                ?: return UnregisterResult.Failure("Student not found")

        if (!studentService.isEligibleToUnregisterStudent(student)) {
            return UnregisterResult.Failure("Student not registered")
        }

        val result =
            runCatching {
                studentRepository.save(student.copy(status = StudentStatus.UNREGISTERED))
            }

        if (result.isSuccess) {
            eventPublisher.publish(StudentUnRegisteredEvent(studentId))
            return UnregisterResult.Success(studentId)
        }

        result.exceptionOrNull()?.printStackTrace()

        val failedReason = result.exceptionOrNull()?.message ?: "Unknown error while persisting student ${student.id}"

        eventPublisher.publish(StudentUpdatedFailedEvent(studentId, action = "UNREGISTER", failedReason))
        return UnregisterResult.Failure(failedReason)
    }
}
