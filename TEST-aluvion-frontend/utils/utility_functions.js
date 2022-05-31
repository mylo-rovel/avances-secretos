// this function attach eventListener to new elements we can create dynamically
// selector can be, for example "div", ".some-class", "#some-id", etc
export function addGlobalEventListener(eventToListen, selector, callback) {
    document.addEventListener(eventToListen, eventObj => {
        if (eventObj.target.matches(selector)) {callback(eventObj);}
    });
}