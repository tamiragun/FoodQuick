FROM openjdk:16
COPY ./out/production/Task_11_Capstone/ /tmp
WORKDIR /tmp
ENTRYPOINT ["java","Main"]