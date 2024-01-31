function HTMLActuator(){this.tileContainer=document.querySelector(".tile-container");this.scoreContainer=document.querySelector(".score-container");this.bestContainer=document.querySelector(".best-container");this.messageContainer=document.querySelector(".game-message");this.score=0}HTMLActuator.prototype.actuate=function(c,b){var a=this;window.requestAnimationFrame(function(){a.clearContainer(a.tileContainer);c.cells.forEach(function(d){d.forEach(function(e){if(e){a.addTile(e)}})});a.updateScore(b.score);a.updateBestScore(b.bestScore);if(b.terminated){if(b.over){if(b.won&&!b.keepPlaying){a.message(true);return}a.message(false)}else{if(b.won){a.message(true)}}}})};HTMLActuator.prototype.continueGame=function(){this.clearMessage()};HTMLActuator.prototype.clearContainer=function(a){while(a.firstChild){a.removeChild(a.firstChild)}};HTMLActuator.prototype.addTile=function(f){var d=this;var h=document.createElement("div");var c=document.createElement("div");var b=f.previousPosition||{x:f.x,y:f.y};var g=this.positionClass(b);var e=["tile","tile-"+f.value,g];if(f.value>2048){e.push("tile-super")}this.applyClasses(h,e);c.classList.add("tile-inner");c.textContent=f.value;if(f.previousPosition){var a=d.positionClass({x:f.x,y:f.y});if(a!==e[2]){window.requestAnimationFrame(function(){e[2]=a;d.applyClasses(h,e)})}}else{if(f.mergedFrom){e.push("tile-merged");this.applyClasses(h,e);f.mergedFrom.forEach(function(i){d.addTile(i)})}else{e.push("tile-new");this.applyClasses(h,e)}}h.appendChild(c);this.tileContainer.appendChild(h)};HTMLActuator.prototype.applyClasses=function(b,a){b.setAttribute("class",a.join(" "))};HTMLActuator.prototype.normalizePosition=function(a){return{x:a.x+1,y:a.y+1}};HTMLActuator.prototype.positionClass=function(a){a=this.normalizePosition(a);return"tile-position-"+a.x+"-"+a.y};HTMLActuator.prototype.updateScore=function(b){this.clearContainer(this.scoreContainer);var c=b-this.score;this.score=b;this.scoreContainer.textContent=this.score;if(c>0){var a=document.createElement("div");a.classList.add("score-addition");a.textContent="+"+c;this.scoreContainer.appendChild(a)}};HTMLActuator.prototype.updateBestScore=function(a){this.bestContainer.textContent=a};HTMLActuator.prototype.message=function(c){var a=c?"game-won":"game-over";var b=c?i18n.get("you_win"):i18n.get("game_over");this.messageContainer.classList.add(a);this.messageContainer.getElementsByTagName("p")[0].textContent=b};HTMLActuator.prototype.promptRestart=function(){this.clearMessage();var a=i18n.get("start_a_new_game");this.messageContainer.classList.add("restart-game");this.messageContainer.getElementsByTagName("p")[0].textContent=a};HTMLActuator.prototype.clearMessage=function(){this.messageContainer.classList.remove("game-won");this.messageContainer.classList.remove("game-over");this.messageContainer.classList.remove("restart-game");this.messageContainer.classList.remove("undo-move")};HTMLActuator.prototype.promptUndo=function(){this.clearMessage();var a=i18n.get("undo_the_current_move");this.messageContainer.classList.add("undo-move");this.messageContainer.getElementsByTagName("p")[0].textContent=a};
