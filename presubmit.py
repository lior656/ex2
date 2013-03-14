#! /usr/bin/env python
import sys, os
from xml.etree import ElementTree
from xml.etree.ElementTree import Element

NS_MAP = {
	"ns0": "http://schemas.android.com/apk/res/android"
}

def main(argv):
	projectDirectory = "TodoListManager"
	manifestPath = "%s/AndroidManifest.xml" % projectDirectory
	package = "il.ac.huji.todolist"
	activityName = "TodoListManagerActivity"
	gitRepoTag = "v1"
	views = [
		("layout", "EditText[@ns0:id='@+id/edtNewItem']"),
		("layout", "ListView[@ns0:id='@+id/lstTodoItems']"),
		("menu", "item[@ns0:id='@+id/menuItemAdd']"),
		("menu", "item[@ns0:id='@+id/menuItemDelete']"),
	]
	
	activityFullName = "%s.%s" % (package, activityName)
	gitTagPath = os.path.join(".git", "refs", "tags", gitRepoTag)
	
	
	if not os.path.isfile(manifestPath):
		die("cannot find android manifest at: %s" % manifestPath)
	manifest = ElementTree.parse(manifestPath)
	manifest = manifest.getroot()
#	print ElementTree.tostring(manifest)
	if manifest.attrib["package"] != package:
		die("Invalid package.")
	activ = manifest.find("application/activity[@ns0:name='" + activityFullName + "']" , NS_MAP)
	if activ is None:
		die("Cannot find activity %s at android manifest: %s" % ("activName", manifestPath))
	
	for directoryName, xPath in views:
		directory = os.path.join(projectDirectory, "res", directoryName)
		found_xPath = False
		for xmlName in listFiles(directory, ".xml"):
			xmlPath = os.path.join(directory, xmlName)
			xml = ElementTree.parse(xmlPath)
			xml = xml.getroot()
			e = xml.find(xPath, NS_MAP)
#			print ElementTree.tostring(xml)
#			print e
			found_xPath = e is not None
			if found_xPath:
				break
		if not found_xPath:
			die("Cannot find xPath: %s" % xPath)
	
	if not os.path.isfile(gitTagPath):
		die("Cannot find git tag: %s" % gitRepoTag)
	print ("All tests completed OK.")

def listFiles(path, ext):
	for name in os.listdir(path):
		ext_ = os.path.splitext(name)[1]
		if ext_.lower() == ext.lower():
			yield name

def die(msg, errorcode = -1):
	print >> sys.stderr, "ERROR:", msg
	exit(errorcode)

def folderTest():
	return os.path.isdir("TodoListManager")
folderTest.errorMsg = "Cannot find TodoListManager directory."
folderTest.errno = -1

if __name__ == "__main__":
	main(sys.argv[1:])