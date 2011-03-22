function tab(b, f, e) {
	for (i = 1; i <= e; i++) {
		var d = document.getElementById(b + i);
		var a = document.getElementById("c-" + b + i);
		d.className = i == f ? "hover" : "";
		a.style.display = i == f ? "block" : "none"
	}
}
function setHomePage(f) {
	var b = document.URL.split("/");
	var c = "http://" + b[2] + "/";
	try {
		f.style.behavior = "url(#default#homepage)";
		f.setHomePage(c)
	} catch (d) {
		if (window.netscape) {
			try {
				netscape.security.PrivilegeManager
						.enablePrivilege("UniversalXPConnect")
			} catch (d) {
				alert("\u6b64\u64cd\u4f5c\u88ab\u6d4f\u89c8\u5668\u62d2\u7edd\uff01\n\u8bf7\u5728\u6d4f\u89c8\u5668\u5730\u5740\u680f\u8f93\u5165\u201cabout:config\u201d\u5e76\u56de\u8f66\n\u7136\u540e\u5c06[signed.applets.codebase_principal_support]\u8bbe\u7f6e\u4e3a'true'")
			}
			var a = Components.classes["@mozilla.org/preferences-service;1"]
					.getService(Components.interfaces.nsIPrefBranch);
			a.setCharPref("browser.startup.homepage", c)
		}
	}
	if (window.netscape) {
		alert("ff")
	}
}
function addFavorite() {
	var a = document.URL.split("/");
	var c = "http://" + a[2] + "/";
	var b = document.title;
	try {
		window.external.AddFavorite(c, b)
	} catch (d) {
		window.sidebar.addPanel(b, c, "")
	}
};