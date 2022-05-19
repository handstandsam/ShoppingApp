import SwiftUI
import multiplatform

struct ContentView: View {
    let greet = Greeting().greeting()
    let user = User(firstname: "handstands", lastname: "sam")
	var body: some View {
		Text(user.description())
        Text(greet)
        Text(greet)
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
