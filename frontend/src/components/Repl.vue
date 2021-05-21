<template>
  <div class="repl">
    <h1>{{ msg }}</h1>
    <p>
      <Containers></Containers>
    </p>
  </div>
</template>

<script>
import Containers from "@/components/Containers";

export default {
  name: 'Repl',
  props: {
    msg: String
  },
  components: {
    Containers,
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
