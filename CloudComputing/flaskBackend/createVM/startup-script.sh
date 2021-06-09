#!/bin/bash
VERSION="$(curl -fsSLI -o /dev/null -w "%{url_effective}" https://github.com/cdr/code-server/releases/latest)";
VERSION="${VERSION#https://github.com/cdr/code-server/releases/tag/}";
VERSION="${VERSION#v}";
echo "$VERSION";
echo "Installing v$VERSION package from GitHub releases.";
sudo apt update;
sudo apt-get install wget -y < "/dev/null";
FILE="code-server-${VERSION}-linux-amd64.tar.gz";
wget --no-check-certificate --content-disposition "https://github.com/cdr/code-server/releases/download/v$VERSION/code-server-${VERSION}-linux-amd64.tar.gz";
sudo tar -xvzf code-server-${VERSION}-linux-amd64.tar.gz ;
rm code-server-${VERSION}-linux-amd64.tar.gz ;
sudo mv code-server-${VERSION}-linux-amd64 code-server
code-server/bin/code-server --bind-addr 0.0.0.0:8080 --user-data-dir /var/lib/code-server --auth none

# sudo chmod +x -R /usr/lib/code-server;
# sudo ln -s /usr/lib/code-server/bin/code-server /usr/bin/code-server;
# sudo chmod +x -R /usr/bin/code-server;
# sudo mkdir -p /var/lib/code-server;
# sudo chmod +wx -R /var/lib/code-server;
# sudo touch /lib/systemd/system/code-server.service;
# echo "[Unit]
# Description=code-server
# After=nginx.service

# [Service]
# Type=simple
# ExecStart=/usr/bin/code-server --bind-addr 0.0.0.0:8080 --user-data-dir /var/lib/code-server --auth none
# Restart=always

# [Install]
# WantedBy=multi-user.target" | sudo tee /lib/systemd/system/code-server.service;
# sudo systemctl daemon-reload;
# sudo systemctl start code-server;
# sudo systemctl enable code-server;
# sudo systemctl restart code-server;
# sudo apt update;
# sudo apt-get install ufw -y < "/dev/null";
# sudo ufw allow 'Nginx HTTP';
# sudo ufw allow 22;
# sudo ufw allow ssh;
# sudo ufw allow 80;
# sudo ufw allow 8080;
# sudo ufw --force enable; 
