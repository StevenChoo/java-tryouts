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

package dev.choo.example.school.student.infrastructure.events

import java.util.UUID

sealed interface StudentEvent {
    val studentId: UUID
}

data class StudentRegisteredEvent(
    override val studentId: UUID,
) : StudentEvent

data class StudentUnRegisteredEvent(
    override val studentId: UUID,
) : StudentEvent

data class StudentDeletedEvent(
    override val studentId: UUID,
) : StudentEvent

data class StudentUpdatedFailedEvent(
    override val studentId: UUID,
    val action: String,
    val reason: String,
) : StudentEvent
