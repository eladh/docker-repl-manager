<template>
  <div>
<!--    todo repeat on all containers-->
    <button v-show="!this.display" class="button line" @click="load(1)">
      Container 1 <br>
    </button>
    <button v-show="!this.display" class="button line" @click="load(2)" >
      Container 2 <br>
    </button>
    <button v-show="!this.display" @click="test" class="button line add-button">
      + <br>
    </button>
<!--   class="input-select list all machine types-->
    <div>
      <label>
        <select v-model="activeContainer">
          <option v-for="option in options" v-bind:value="option" v-bind:key="option">
            {{ option }}
          </option>
        </select>
      </label>
      <button @click="create">Add</button>
    </div>

    <div>
      <Terminal v-bind:container="this.activeContainer" :key="activeContainer"></Terminal>
    </div>

  </div>
</template>

<script>
import Terminal from "@/components/Terminal";

export default {
  name: "Containers",
  data() {
    return {
      activeContainer: 0,
      display: false,
      options: ["machine 1" , "machine 2"]
    }
  },
  methods: {
    load: function (id) {
      console.log(id);
      this.activeContainer = id;
    },
    create: function () {

    },
    test: function () {
      this.display = true;
      console.log("some yest method");
    }
  },
  components: {
    Terminal
  }
}
</script>

<style scoped>
.button {
  background-color: #4CAF50;
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  height: 100px;
}

.add-button {
  background-color: gray;
}


</style>
