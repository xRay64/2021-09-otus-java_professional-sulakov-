const streamErr = e => {
    console.warn("error");
    console.warn(e);
}

fetch("http://localhost:8080/client/all").then((response) => {
    return can.ndjsonStream(response.body);
}).then(dataStream => {
    const reader = dataStream.getReader();
    const read = result => {
        if (result.done) {
            return;
        }
        render(result.value);
        reader.read().then(read, streamErr);
    }
    reader.read().then(read, streamErr);
});

const render = value => {
    console.log(value);
    for (const row of value) {
        tr = document.createElement('tr');

        id = document.createElement('td');
        id.textContent = row.id;

        clientName = document.createElement('td');
        clientName.textContent = row.name;

        address = document.createElement('td');
        address.textContent = row.address.street;

        phonesString = '';
        for (const phone of row.phones) {
            console.log(phone);
            phonesString += phone.number + '; ';
        }

        phones = document.createElement('td');
        phones.textContent = phonesString;

        tr.append(id);
        tr.append(clientName);
        tr.append(address);
        tr.append(phones);
        document.getElementById('tableBody').append(tr);
    }
    // div.append(new Date() + ' stringValue:', JSON.stringify(value));
};