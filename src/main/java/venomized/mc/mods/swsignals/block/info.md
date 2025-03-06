# Blocks & models
Blocks should be appropriately named;
Category should be separated with `.`. Spaces are replaced with `_` in the ending name. (See example below)
`somecategory.somesubcategory.thisblock_registry_name`

If this block inherits SwAbstract45DegreeBlock.java class.
A corresponding model file must exist in the the assets folder accordingly.
Taking the above example. A model file would be searched at
`src/main/resources/assets/swsignals/models/block/somecategory/somesubcategory/thisblock_registry_name.json`.