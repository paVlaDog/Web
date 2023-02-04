<template>
    <div id="app">
        <Header :user="user"/>
        <Middle :state="state" :user="user" :posts="posts" :users="users" :comments="comments"/>
        <Footer/>
    </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";
import axios from "axios"

export default {
    name: 'App',
    components: {
        Footer,
        Middle,
        Header
    },
    data: function () {
        return {
            user: null,
            posts: [],
            users: [],
            comments: [],
            state: null
        }
    },
    beforeMount() {
      if (localStorage.getItem("jwt") && !this.user) {
          this.$root.$emit("onJwt", localStorage.getItem("jwt"));
      }

      axios.get("/api/1/posts").then(response => {
          this.posts = response.data;
      });

      axios.get("/api/1/users").then(response => {
        this.users = response.data;
      });

      axios.get("/api/1/comments").then(response => {
        this.comments = response.data;
      });

      axios.get("/api/1/state").then(response => {
        this.state = response.data;
      });
    },
    beforeCreate() {
      this.$root.$on("onRegister", (login, nameUser, password) => {
        if (!this.user) {
          if (!login || login.trim().length < 2 || login.length > 24) {
            this.$root.$emit("onRegisterValidationError", "Login length from 2 to 24");
          } else if (Object.values(this.users).map(u => u.login).includes(login)) {
            this.$root.$emit("onRegisterValidationError", "Login is required");
          } else if (!nameUser || nameUser.trim().length < 1 || nameUser.length > 100) {
            this.$root.$emit("onRegisterValidationError", "Name length from 1 to 100");
          } else if (!password || password.trim().length < 4 || password.length > 12) {
            this.$root.$emit("onRegisterValidationError", "Password length from 4 to 12");
          } else {
            axios.post("/api/1/users", {
              login, name: nameUser, password
            }).then(response => {
              axios.get("/api/1/users").then(response => {
                this.users = response.data;
              }).then(() => {
                localStorage.setItem("jwt", response.data);
                this.$root.$emit("onJwt", response.data);
              });
            }).catch(error => {
              this.$root.$emit("onRegisterValidationError", error.response.data);
            });
          }
        } else {
          this.$root.$emit("onRegisterValidationError", "You already log in");
        }
      });

      this.$root.$on("createBoard", (size, needToWin) => {
        if (!size || size.length < 1 || size.length > 3) {
          this.$root.$emit("onTicTacToeValidationError", "Size is too short or too long");
        } else if (!needToWin || needToWin.length < 1 || needToWin.length > 3) {
          this.$root.$emit("onTicTacToeValidationError", "NeedToWin is too short or too long");
        } else {
          axios.post("/api/1/createBoard", {
            size, needToWin
          }).then(() => {
            axios.get("/api/1/state").then(response => {
              this.state = response.data;
            })
          }).catch(error => {
            this.$root.$emit("onTicTacToeValidationError", error.response.data);
          });
        }}
      );

      this.$root.$on("onMove", (row, col) => {
        axios.post("/api/1/move", {
          row, col
        }).then(() => {
          axios.get("/api/1/state").then(response => {
            this.state = response.data;
          })
        }).catch(error => {
          this.$root.$emit("onTicTacToeValidationError", error.response.data);
        });
      });

      this.$root.$on("newGame", () => {
        axios.post("/api/1/newGame", {
          row: 0, col: 0
        }).then(() => {
          axios.get("/api/1/state").then(response => {
            this.state = response.data;
            this.$root.$emit("onChangePage", 'TicTacToe')
          })
        }).catch(error => {
          this.$root.$emit("onTicTacToeValidationError", error.response.data);
        });
      });

      this.$root.$on("onEnter", (login, password) => {
          if (password === "") {
              this.$root.$emit("onEnterValidationError", "Password is required");
              return;
          }

          axios.post("/api/1/jwt", {
                  login, password
          }).then(response => {
              localStorage.setItem("jwt", response.data);
              this.$root.$emit("onJwt", response.data);
          }).catch(error => {
              this.$root.$emit("onEnterValidationError", error.response.data);
          });
      });

      this.$root.$on("onJwt", (jwt) => {
        localStorage.setItem("jwt", jwt);

        axios.get("/api/1/users/auth", {
          params: {
            jwt
          }
        }).then(response => {
          this.user = response.data;
          this.$root.$emit("onChangePage", "Index");
        }).catch(() => this.$root.$emit("onLogout"))
      });

      this.$root.$on("onLogout", () => {
        localStorage.removeItem("jwt");
        this.user = null;
      });

      this.$root.$on("onWritePost", (title, text) => {
        if (this.user) {
          if (!title || title.length < 1 || title.length > 100) {
            this.$root.$emit("onWritePostValidationError", "Title is too short");
          } else if (!text || text.length < 1 || text.length > 10000) {
            this.$root.$emit("onWritePostValidationError", "Text is too short");
          } else {
            axios.post("/api/1/posts", {
              title, text, user: this.user
            }).then(() => {
              axios.get("/api/1/posts").then(response => {
                this.posts = response.data;
              }).then(() => {
                this.$root.$emit("onChangePage", "Index");
              })
            }).catch(error => {
              this.$root.$emit("onWritePostValidationError", error.response.data);
            });
          }
        } else {
          this.$root.$emit("onWritePostValidationError", "No access");
        }
      });

      this.$root.$on("onWriteComment", (text, post) => {
        if (this.user) {
          if (!text || text.trim().length < 1) {
            this.$root.$emit("onWriteCommentValidationError", "Text is empty!");
          } else {
            axios.post("/api/1/comments", {
              text, post, user: this.user
            }).then(() => {
              axios.get("/api/1/comments").then(response => {
                this.comments = response.data;
              }).then(() => {
                this.$root.$emit("onLinkToPostPage", post);
              })
            }).catch(error => {
              this.$root.$emit("onWriteCommentValidationError", error.response.data);
            });
          }
        } else {
          this.$root.$emit("onWriteCommentValidationError", "No access");
        }
      });

    }
}
</script>

<style>
#app {

}
</style>
