FROM postgres:11

RUN if [ $(command -v apt-get) ]; then apt-get update && apt-get install -y python3 sudo bash ca-certificates iproute2 && apt-get clean; \
    elif [ $(command -v dnf) ]; then dnf makecache && dnf --assumeyes install python3 which sudo python3-devel python3*-dnf bash iproute && dnf clean all; \
    elif [ $(command -v yum) ]; then yum makecache fast && yum install -y python sudo yum-plugin-ovl bash iproute && sed -i 's/plugins=0/plugins=1/g' /etc/yum.conf && yum clean all; \
    elif [ $(command -v zypper) ]; then zypper refresh && zypper install -y python sudo bash python-xml iproute2 && zypper clean -a; \
    elif [ $(command -v apk) ]; then apk update && apk add --no-cache python sudo bash ca-certificates; \
    elif [ $(command -v xbps-install) ]; then xbps-install -Syu && xbps-install -y python sudo bash ca-certificates iproute2 && xbps-remove -O; fi