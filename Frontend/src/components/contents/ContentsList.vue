<template>
<div>
    {{content}}    
</div>
</template>
<script>
export default {
    name: "contentsList",
    props:{
        content : {
            type : String
        }
    }
}
</script>
<style scoped>

</style>