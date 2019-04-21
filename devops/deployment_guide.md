# Deployment Guide
This guide documents the steps performed in order to deliver the software to the vagrant or production servers.

## Software Packaging
Execute the following commands in @/backend:

    ./gradlew clean build docker dockerArchive

## OS Configuration
Execute the following commands in @/devops.

    # Vagrant
    ansible-playbook -i environments/vagrant os.yml
    # Production
    ansible-playbook -i environments/production os.yml

## Software Deployment
Execute the following commands in @/devops.

    # Vagrant
    ansible-playbook -i environments/vagrant webapp.yml -e "webapp_image=railway-routing-service:1.0.0"
    # Production
    ansible-playbook -i environments/production webapp.yml -e "webapp_image=railway-routing-service:1.0.0"