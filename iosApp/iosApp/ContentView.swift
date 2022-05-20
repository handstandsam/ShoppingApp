import SwiftUI
import multiplatform

struct ContentView: View {
    
    func something() {
        let graph = IosMultiplatformApi().networkGraph.categoryRepo.getCategories {
            data, error in
            if let people = data {
                print(people)
            }
            if let errorReal = error {
                print(errorReal)
            }
        }
    }
    
    init(){
        something()
    }

    
    let greet = Greeting().greeting()
    let user = ModelsUser(firstname: "handstands", lastname: "sam")
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
