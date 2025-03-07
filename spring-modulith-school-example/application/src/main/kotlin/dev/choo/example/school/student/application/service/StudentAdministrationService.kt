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

import dev.choo.example.school.student.domain.model.Student
import dev.choo.example.school.student.domain.repository.StudentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional // By default, we want all operations transactional within a Service
class StudentAdministrationService(
    private val studentRepository: StudentRepository,
) {
    @Transactional(readOnly = true)
    fun getAllStudents(): List<Student> = studentRepository.getAll()

    @Transactional(readOnly = true)
    fun getStudent(studentId: UUID): Student? = studentRepository.findById(studentId)
}
