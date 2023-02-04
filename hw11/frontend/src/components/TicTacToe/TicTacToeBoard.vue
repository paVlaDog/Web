<template>
  <div v-if="state">
    <h1>Tic-Tac-Toe</h1>
    <div class="tictactoe-board">
      <TicTacToeRow v-for="(num, i) in state.cells" :row="i" :key="i" :rowVal="num" :crossesMove="crossesMove"/>
    </div>

    <div class="tictactoe-message">
      <div v-if="state.phase === 'RUNNING'">Move: {{crossesMove}}</div>
      <div v-if="state.phase === 'WON_X'">Game over. Xs won!</div>
      <div v-if="state.phase === 'WON_O'">Game over. Os won!</div>
      <div v-if="state.phase === 'DRAW'">Game over. Draw!</div>
    </div>

    <div>
      <div v-if="state.phase !== 'RUNNING'">
        <form @submit.prevent="newGame" method="post">
          <input type="hidden" name="action" value="newGame">
          <input type="submit" class="tictactoe-new-game" name="newGame" value="New Game!">
        </form>
      </div>
    </div>
  </div>

  <div v-else>
    <TicTacToeForm/>
  </div>

</template>


<script>
  import TicTacToeRow from "@/components/TicTacToe/TicTacToeRow";
  import TicTacToeForm from "@/components/TicTacToe/TicTacToeForm";
  export default {
    name: "TicTacToeBoard",
    components: {TicTacToeForm, TicTacToeRow},
    props: ["state"],
    data: function () {
      return {
        crossesMove: this.state.crossesMove ? "X" : "0"
      }
    },
    methods: {
      newGame: function () {
        this.$root.$emit("newGame")
      }
    },
    beforeCreate() {
      this.$root.$on("changeCrossesMove", () => {
        this.crossesMove = this.crossesMove === "X" ? "0" : "X";
      })
    },
  }
</script>

<style scoped>
</style>