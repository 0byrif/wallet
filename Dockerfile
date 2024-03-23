FROM gradle:jdk17

WORKDIR /WalletManager

COPY /WalletManager .

RUN gradle installDist

CMD ./build/install/WalletManager/bin/WalletManager