package ru.projektio.userservice.dto.mappers

interface AbstractMapper<in E, out R> {
    fun mapEntityToResponse(entity: E): R
}