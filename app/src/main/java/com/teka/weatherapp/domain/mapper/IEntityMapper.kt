package com.teka.weatherapp.domain.mapper

interface IEntityMapper<Entity, Model> {

    fun mapFromEntity(entity: Entity) : Model

    fun entityFromModel(model: Model) : Entity
}