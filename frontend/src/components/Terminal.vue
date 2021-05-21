<template>
  <div class="box">
    <div id="xterm"></div>
  </div>
</template>

<script>
import {Terminal} from "xterm";
import "xterm/css/xterm.css";
import {getStompClient, subscribe} from "@/services/WebSocketService";

export default {
  name: "Terminal",
  props: {
    container: {
      id: String,
      required: true,
    },
  },
  data: () => {
    return {
      display: false
    }
  },
  mounted() {
    console.log("load new terminal!!!!!!!!!!!! =" + this.container)
    subscribe("/topic/terminal-response/" + this.container, (data) => {
      this.term.write(data + "\n");
    });
    this.term = new Terminal({
      rendererType: "canvas", //Rendering type
      rows: 20, //Rows
      convertEol: true, //When enabled, the cursor will be set to the beginning of the next line
      scrollback: 10, //The amount of rollback in the terminal
      disableStdin: false, //Whether input should be disabled
      cursorStyle: "underline", //Cursor style
      cursorBlink: true, //Cursor blinks
      theme: {
        foreground: "yellow", //Font
        background: "#060101", //Background color
        cursor: "help" //Set cursor
      }
    });
    this.term.open(document.getElementById("xterm"));

    this.term.onKey((ev) => {
      if (ev.domEvent.code === "Enter") {
        this.term.write("\n");
        getStompClient().send("/app/terminal/send", JSON.stringify(
            {containerId: this.container, instruction: this.command}), {});
        this.command = "";
      }
      this.command = updateTerminalInput(ev, this.command, this.term);
    });
  }
}

function updateTerminalInput(ev, line = "", term) {
  const printable = !ev.domEvent.altKey && !ev.domEvent.ctrlKey
      && !ev.domEvent.metaKey;

  if (ev.domEvent.code === "Backspace") {
    if (line && line.length > 1) {
      term.write('\b \b');
      return line.slice(0, -1);
    }
  } else if (printable) {
    term.write(ev.key);
    return line + ev.key;
  }
}

</script>

<style scoped>

</style>
