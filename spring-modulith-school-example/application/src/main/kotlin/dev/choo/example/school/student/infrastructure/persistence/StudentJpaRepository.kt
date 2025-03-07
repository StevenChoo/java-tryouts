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

package dev.choo.example.school.student.infrastructure.persistence

import dev.choo.example.school.student.domain.model.Student
import dev.choo.example.school.student.domain.repository.StudentRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import java.util.UUID

interface StudentJpaRepository : JpaRepository<StudentJpaEntity, Long> {
    fun findByStudentId(studentId: UUID): StudentJpaEntity?

    fun existsByStudentId(studentId: UUID): Boolean

    fun deleteByStudentId(studentId: UUID): Long
}

/**
 * Using a JpaInterface and RepositoryImpl pattern, so we can inherit the Spring JpaRepository needed to bootstrap the repository.
 */
@Component
class StudentRepositoryImpl(
    private val studentJpaRepository: StudentJpaRepository,
) : StudentRepository {
    override fun getAll(): List<Student> = studentJpaRepository.findAll().map { it.toDomain() }

    override fun findById(id: UUID): Student? = studentJpaRepository.findByStudentId(id)?.toDomain()

    override fun save(student: Student): Student {
        val studentEntity = StudentJpaEntity.fromDomain(student)
        return studentJpaRepository.save(studentEntity).toDomain()
    }

    override fun delete(id: UUID): Boolean =
        if (studentJpaRepository.existsByStudentId(id)) {
            studentJpaRepository.deleteByStudentId(id)
            true
        } else {
            false
        }

    override fun exists(id: UUID): Boolean = studentJpaRepository.existsByStudentId(id)
}
