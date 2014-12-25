(function(){

	var editor = CodeMirror(document.body, {
		keyMap: 'sublime',
		mode: 'text/x-mysql',
		indentWithTabs: true,
		smartIndent: true,
		lineNumbers: true,
		matchBrackets : true,
		autofocus: true,
		extraKeys: {"Ctrl-Space": "autocomplete"},
		theme: 'ambiance'
	}),
	w = $(window),
	ww, wh;
	
	w.on('resize load', function(){
		
		ww = w.width();
		wh = w.height();
		
		editor.setSize(null, wh);
		
	});
	
	window.editor = editor;
	
})();