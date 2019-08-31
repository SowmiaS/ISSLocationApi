package com.wethejumpingspiders.isslocationapi.sightingsparser

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.io.Serializable


@Root(name = "rss", strict = false)
class Feed() : Serializable {

    @set:Element(name = "channel")
    @get:Element(name = "channel")
    public var mChannel: Channel? = null

}

@Root(name = "channel", strict = false)
class Channel() : Serializable {

    @set:ElementList(name = "item", inline = true, empty = true, required = false)
    @get:ElementList(name = "item", inline = true, empty = true, required = false)
    public var sightingItems: List<SightingItem>? = null

}

@Root(name = "item", strict = false)
class SightingItem() : Serializable {

    @set:Element(name = "description")
    @get:Element(name = "description")
    public var description: String? = null

}