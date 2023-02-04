<template>
  <div class="enter form-box">
    <div class="header">New Game</div>
    <div class="body">
      <form @submit.prevent="createBoard">
        <input type="hidden" name="action" value="enter"/>
        <div class="field">
          <div class="name">
            <label for="size">Size:</label>
          </div>
          <div class="value">
            <input id="size" name="size" v-model="size"/>
          </div>
        </div>
        <div class="field">
          <div class="name">
            <label for="needToWin">Need to win:</label>
          </div>
          <div class="value">
            <input id="needToWin" name="needToWin" v-model="needToWin"/>
          </div>
        </div>
        <div class="error">{{ error }}</div>
        <div class="button-field">
          <input type="submit" value="Enter">
        </div>
      </form>
    </div>
  </div>
</template>


<script>
  export default {
    name: "TicTacToeForm",
    data: function () {
      return {
        size: "",
        needToWin: "",
        error: ""
      }
    },
    methods: {
      createBoard: function () {
        this.error = "";
        this.$root.$emit("createBoard", this.size, this.needToWin);
      }
    },
    beforeCreate() {
      this.$root.$on("onTicTacToeValidationError", error => this.error = error)
    },
  }
</script>

<style scoped>
</style>