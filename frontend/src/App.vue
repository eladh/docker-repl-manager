<template>
  <div id="app">
    <Repl msg="Welcome to Repl App"/>
  </div>
</template>

<script>
import Repl from './components/Repl.vue'
import {initWebSocket} from "@/services/WebSocketService";

export default {
  name: 'App',
  components: {
    Repl
  },
  mounted() {
    initWebSocket();
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
