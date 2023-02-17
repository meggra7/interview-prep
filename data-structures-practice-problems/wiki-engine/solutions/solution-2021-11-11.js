
let storage = new Map();
let storageSize = 0;
let lastKey = 0;


function createEntry(text) {

    let key = lastKey + 1;

    let entry = {
        id: key,
        text: text,
        created: _getCurrentTimeStamp(),
        modified: null
    };

    storage.set(key, entry);
    lastKey = key;

    return entry;
}

modifyEntry(id, text) {

    entry = storage.get(id);
    entry.text = text;
    entry.modified = _getCurrentTimeStamp();

    return entry;
}

deleteEntry(id) {
    storage.delete(id);
}

search(id) {
    return storage.get(id);
}


getEntriesByCreation() {

    let list = new Array();

    for (let key = 1; key <= lastKey; key++) {

        if (storage.get(key)) {
            let currentEntry = storage.get(key);
            list.push(currentEntry);
        }
    }

    return list;
}

getLastTen() {

    list = getEntriesByCreation();

    sortedList = list.sort(_comparator);

    let listToReturn = [];

    for (let i = 0; i < sortedList.length && i < 10; i++) {
        listToReturn.push(sortedList[i]);
    }

    return listToReturn;

}

_getCurrentTimeStamp() {
    return new Date();
}

_comparator(a, b) {

    const aValue = a.modified;
    const bValue = b.modified;

    if (aValue > bValue) {
        return -1;
    } else if (aValue == bValue) {
        return 0;
    } else { // bValue > aValue)
        return 1;
    };
}