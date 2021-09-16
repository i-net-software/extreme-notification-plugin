package org.jenkinsci.plugins.extremenotification.NotificationEndpoint

def f=namespace(lib.FormTagLib)
def st=namespace("jelly:stapler")

def tableOrDiv(Closure closure) {
	if (!context.getVariableWithDefaultValue("divBasedFormLayout", false)) {
		table(width: "100%") {
			closure.call()
		}
		return;
	}

	div() {
		closure.call()
	}
}

st.include(it:instance, 'class':descriptor.clazz, page:"config-advanced", optional:true)
f.advanced {
	events = instance?.events ?: [:]
	f.optionalBlock(name:"events", title:_("All Events"), negative:true, checked:events.isEmpty()) {
		f.nested {
			tableOrDiv {
				my.ENDPOINTS.each { endpoint ->
					f.optionalBlock(name:"event", title:endpoint, checked:events[endpoint] != null) {
						f.invisibleEntry {
							input(type:"hidden", name:"endpoint", value:endpoint)
						}
						f.nested {
							tableOrDiv {
								set('instance', events.get(endpoint))
								st.include(it:instance, 'class':descriptor.clazz, page:"config-advanced-endpoint", optional:true)
							}
						}
					}
				}
			}
		}
	}
}
