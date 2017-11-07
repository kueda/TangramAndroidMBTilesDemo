# Tanagram Android MBTiles Demo

Simple demo for showing how to use an [MBTiles](https://github.com/mapbox/mbtiles-spec) file as a data source with [Tangram ES](https://github.com/tangrams/tangram-es) in Android. The most salient issues I found were that the MBTiles must be in the app's internal or external file system storage, not in assets, because SQLite can't read a file directly from assets.

Here's how I prepped the MBTiles file from [thematicmapping.org's World Borders Dataset](http://thematicmapping.org/downloads/world_borders.php) with [tl](https://github.com/mojodna/tl), since that wasn't immediately obvious to me either. This assumes you have `node` and `npm` installed.

```bash
# Install tilelive stuff
npm install -g tl @mapbox/tilelive-omnivore @mapbox/mbtiles

# Get the data
wget http://thematicmapping.org/downloads/TM_WORLD_BORDERS_SIMPL-0.3.zip
unzip TM_WORLD_BORDERS_SIMPL-0.3.zip

# Create the MBTiles file in the app's assets directory
tl copy -z 0 -Z 5  omnivore:///absolute/path/to/TM_WORLD_BORDERS_SIMPL-0.3.shp mbtiles:///absolute/path/to/TangramAndroidMBTilesDemo/app/src/main/assets/tm_world_borders.mbtiles
```