var conf = {
  x: 0,
  y: 0,
  id: 1,
  termDiv: 'cyberduckDiv',
  frameColor: '#77777a',
  ps: ' YOU: ',
  historyUnique: true,
  initHandler: initHandler,
  exitHandler: termExit,
  handler: termHandler
}

var term = new Terminal(conf);
var cyberduck = new CyberduckBot();

function termOpen() {
  TermGlobals.keylock = false;
  if (term.closed) cyberduck.reset();
  term.open();
  setTermLink(false);
}

function setTermLink(v) {
  var linkobj;
  if (document.getElementById) {
    linkobj = document.getElementById('termOpenLink');
  } else if (document.all) {
    linkobj = document.all.termOpenLink;
  } else {
    linkobj = document.links.termOpenLink;
  }
  if (linkobj) linkobj.className = (v) ? 'termopen' : 'termopenhidden';
}

function initHandler() {
  this.write([
    '  Üdvözöl téged az online chat szobában: ',
    '    _____       _                  _            _     ',
    '   /  __ \\     | |                | |          | |    ',
    '   | /  \\/_   _| |__   ___ _ __ __| |_   _  ___| | __ ',
    '   | |   | | | | \'_ \\ / _ \\ \'__/ _` | | | |/ __| |/ / ',
    '   | \\__/\\ |_| | |_) |  __/ | | (_| | |_| | (__|   <  ',
    '    \\____/\\__, |_.__/ \\___|_|  \\__,_|\\__,_|\\___|_|\\_\\ ',
    '           __/ |                                      ',
    '          |___/',
    ' ',
    '  (Cyberduck megpróbál neked segíteni a problémádban, angolul.)',
    '%n%n'
  ]);
  this.type(' CYBERDUCK: ' + cyberduckInitials[Math.floor(Math.random() * cyberduckInitials.length)]);
  this.prompt();
}

function termHandler() {
  var line = this.lineBuffer;
  // no action on empty line
  if (line.search(/^\s*$/) == 0) {
    this.prompt();
    return;
  }
  // transform
  this.write('%n CYBERDUCK: ' + cyberduck.transform(line));
  if (cyberduck.quit) {
    setTimeout('term.close()', 2500);
    return;
  }
  this.prompt();
}

function termExit() {
  TermGlobals.keylock = true;
  setTermLink(true);
}