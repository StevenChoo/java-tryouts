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
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID
import dev.choo.example.school.student.domain.model.StudentStatus as StudentStatusDomain

enum class StudentStatus {
    REGISTERED,
    UNREGISTERED,
    SUSPENDED,
    GRADUATED,
}

@Entity
@Table(name = "STUDENT")
@Suppress("JpaObjectClassSignatureInspection") // Using Kotlin JPA plugin in pom.xml
class StudentJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false, unique = true)
    val studentId: UUID,
    @Column(nullable = false)
    val firstName: String,
    @Column(nullable = false)
    val lastName: String,
    @Column(nullable = false)
    val dateOfBirth: Instant, // For birthdays the timezone is not relevant, hence using Instant
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val status: StudentStatus,
) {
    fun toDomain(): Student =
        Student(
            id = studentId,
            firstName = firstName,
            lastName = lastName,
            dateOfBirth = dateOfBirth,
            status = StudentStatusDomain.valueOf(status.name),
        )

    companion object {
        fun fromDomain(student: Student): StudentJpaEntity =
            StudentJpaEntity(
                studentId = student.id,
                firstName = student.firstName,
                lastName = student.lastName,
                dateOfBirth = student.dateOfBirth,
                status = StudentStatus.valueOf(student.status.name),
            )
    }
}
