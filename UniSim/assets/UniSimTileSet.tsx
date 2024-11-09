<?xml
version = "1.0"
encoding = "UTF-8" ?
>
<
tileset
version = "1.10"
tiledversion = "1.11.0"
name = "UniSimTileSet"
tilewidth = "16"
tileheight = "16"
tilecount = "13"
columns = "0" >
    < grid
orientation = "orthogonal"
width = "1"
height = "1" / >
    < tile
id = "0" >
    < image
source = "Grass.png"
width = "16"
height = "16" / >
    < /tile>
<tile id="1">
    <image source="Water.png" width="16" height="16"/>
</tile>
<tile id="3">
    <image source="Select.png" width="16" height="16"/>
</tile>
<tile id="4">
    <image source="EmptyTile.png" width="16" height="16"/>
</tile>
<tile id="5">
    <image source="notbuildable.png" width="16" height="16"/>
</tile>
<tile id="6">
    <properties>
        <property name="BuildingID" type="int" value="0"/>
    </properties>
    <image source="buildings/halls.png" width="16" height="16"/>
</tile>
<tile id="7">
    <properties>
        <property name="BuildingID" type="int" value="1"/>
    </properties>
    <image source="buildings/bar.png" width="16" height="16"/>
</tile>
<tile id="8">
    <properties>
        <property name="BuildingID" type="int" value="2"/>
    </properties>
    <image source="buildings/lecture_theatre.png" width="16" height="16"/>
</tile>
<tile id="9">
    <properties>
        <property name="BuildingID" type="int" value="3"/>
    </properties>
    <image source="buildings/restaurant.png" width="16" height="16"/>
</tile>
<tile id="10">
    <properties>
        <property name="BuildingID" type="int" value="4"/>
    </properties>
    <image source="buildings/road.png" width="16" height="16"/>
</tile>
<tile id="11">
    <properties>
        <property name="BuildingID" type="int" value="5"/>
    </properties>
    <image source="buildings/sports_hall.png" width="16" height="16"/>
</tile>
<tile id="12">
    <properties>
        <property name="BuildingID" type="int" value="6"/>
    </properties>
    <image source="buildings/bus_stop.png" width="16" height="16"/>
</tile>
<tile id="13">
    <image source="buildings/tree.png" width="16" height="16"/>
</tile>
<tile id="14">
    <image source="buildings/PLACEHOLDER.png" width="16" height="16"/>
</tile>
<tile id="20">
    <image source="buildings/under_construction.png" width="16" height="16"/>
</tile>


</tileset>
