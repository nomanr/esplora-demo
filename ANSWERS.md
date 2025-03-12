## Questions

**1. If the address list contains more than one hundred addresses, which solution do you propose to optimize network consumption, reduce waiting time and avoid conflicts when refreshing the data?**
If we go with the current approach, which is, polling 10 addresses every 10 seconds. This will choke the network of the app if we send 100 requests in a go. This is not ideal for larger number of addresses.

**_Low effort solution (app side)_**

- Batch requests in chunks, e.g., 10 requests every N seconds. This refreshes all data in (totalAddress/10)\*N seconds.
- Poll for updates when the total balance is visible. Stop bulk updates if scrolls away and only observe visible addresses when the user scrolls.
- Poll only recently updated addresses based on defined requirements(e.g. last updated 5 days ago)

**_High effort solution (backend)_**

- Backend should accept bulk addresses.
- Preferably, use a socket-based backend to watch addresses.

**2. A transaction could transition from the unconfirmed to the confirmed state. What strategy could be used to reduce the number of calls or the data exchanges with the esplora api?**
- Since confirm transactions cannot change, we should only poll unconfirmed transactions to check for status change. 
- If the number of unconfirmed transactions are larger in number, we can batch the requests (as discussed above for address case.)

**3. A bitcoin address is a sensitive information that should remain private. Being able to associate it with a specific device should be avoided. Which strategy do you suggest to keep the address list safe?**
This is an open ended questions, depends on what we are trying to achieve. But in terms of mobile app, I can think of a few things: 

- Never store address on the plain text storages, such as files to shared prefs. 
- Store them in a square db, like SQLDelight
- Store addresses in a remote storage and provide the associated public key instead. 
- Make sure you send fake device info whenever making an API call, override any headers that are passed along. 


**4. What could be used in place of an address list?**
Instead of actual address, there are a few things that can be used: 

- derived public keys from the address list 
- watch only address (some wallets provide read only watch only options)

**5. How do you suggest to mitigate man-in-the-middle attacks? (compatible with the esplora api documentation)**

- Warn users if the network allows clear HTTP traffic or if the Wi-Fi is open and unsecured.
- Self-host the Esplora API server and use certificate pinning.
- If possible, create a VPN within the app to route all traffic.

**6. If esplora is hacked and it starts providing fake transactions, how can you realize it? What could you do to mitigate it?**

- Regardless Esplora is hacked or not, if a transaction is confirmed, before taking any actions, it should be verified on blockchain. Since we cannot do it from the mobile app, we can use any other service to verify transactions. 
