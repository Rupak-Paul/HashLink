# HashLink: Decentralized Social Media Application  
HashLink is a decentralized social media application designed to provide privacy, censorship resistance, and user data ownership. Built on the Hedera Hashgraph, HashLink reimagines the social media experience with decentralized identity, secure data storage, and user-driven interactions.  


## Technology Used
The HashLink application is built using the following technologies:

1. **Hedera Network (Backend)**:  
   Hedera's network is used as the backend for the application, leveraging its unique features like high transaction throughput, low costs, and fast finality. The backend utilizes several Hedera services to ensure decentralization, data integrity, and user ownership.

   - **Hedera Consensus Service (HCS)**:  
     This service is used to achieve consensus on user interactions (e.g., posts, reactions, comments) and ensures the immutability and integrity of the data.
   
   - **Hedera Token Service (HTS)**:  
    HTS is used in the application for account creation and posting in a decentralized manner.
   
   - **Hedera File Service (HFS)**:  
     HFS is utilized for decentralized storage, allowing users to securely store and retrieve data, such as images or other media associated with posts, in a decentralized manner.

2. **Android (Frontend)**:  
   The frontend of the HashLink application is developed using Android, providing a smooth user experience with an interface built using Android Studio and Jetpack Compose. The frontend communicates with the Hedera network via the Hedera SDK, enabling users to interact with the decentralized backend seamlessly.
 

## How to Run the Project
To run the HashLink application on your local machine, follow these steps:

1. **Clone the Repository**:  
   Open a terminal and run the following commands to clone the repository:  
   ```bash
   git clone https://github.com/your-repo-link
   cd your-repo-name
   ```

2. **Open the Project in Android Studio**:  
   - Launch Android Studio.  
   - Click on **File > Open** and select the cloned repository folder.  
   - Allow Android Studio to download any required dependencies.

3. **Configure Emulator or Device**:  
   - **To test on an emulator**:  
     - Create a new emulator in Android Studio, preferably a Pixel 7 with Android 13 or higher.  
     - Start the emulator.  
   - **To test on a physical device**:  
     - Enable **Developer Options** and **USB Debugging** on your Android device.  
     - Connect your device to the computer via USB.

4. **Build and Run the Application**:  
   - Click the **Run** button (green triangle) in Android Studio or press `Shift + F10`.  
   - Select the target device (emulator or connected physical device) to deploy the application.

5. **Interact with the Application**:  
   - Use your Hedera testnet credentials to log in or create an account.  
   - Explore the features such as creating posts, reacting, commenting, and more.


## Project Report
You can view the full project report on Overleaf: [Project Report Link](https://www.overleaf.com/read/cbdxdkcxbgnw#dd52d6)
