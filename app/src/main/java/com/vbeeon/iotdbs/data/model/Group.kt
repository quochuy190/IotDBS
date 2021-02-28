package com.vbeeon.iotdbs.data.model

import com.vbeeon.iotdbs.data.local.entity.GroupEntity

data class Group(
        val rn: String,
        val mnm: Int,
        val mt: Int,
        var mid : List<String>
)
fun  convertToEntity(group : Group):GroupEntity{
    return GroupEntity(group.rn, group.mnm, group.mt, group.mid)
}
