package com.huawei.android.bastet;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBastetManager extends IInterface {

    public static abstract class Stub extends Binder implements IBastetManager {
        private static final String DESCRIPTOR = "com.huawei.android.bastet.IBastetManager";
        static final int TRANSACTION_broadcastReceived = 27;
        static final int TRANSACTION_clearProxyById = 7;
        static final int TRANSACTION_configAppUidList = 32;
        static final int TRANSACTION_configDataAccelerate = 34;
        static final int TRANSACTION_hrtAppActivity = 29;
        static final int TRANSACTION_indicateAction = 30;
        static final int TRANSACTION_initEmailProxy = 4;
        static final int TRANSACTION_initHeartbeatProxy = 3;
        static final int TRANSACTION_initHwBastetService = 26;
        static final int TRANSACTION_inquireNetworkQuality = 24;
        static final int TRANSACTION_isProxyProtocolSupport = 2;
        static final int TRANSACTION_isProxyServiceAvailable = 1;
        static final int TRANSACTION_notifyAlarmTimeout = 33;
        static final int TRANSACTION_notifyNrtTimeout = 23;
        static final int TRANSACTION_packageChangedReceived = 28;
        static final int TRANSACTION_prepareHeartbeatProxy = 25;
        static final int TRANSACTION_setDeviceId = 15;
        static final int TRANSACTION_setExchangeHttpHeader = 14;
        static final int TRANSACTION_setFilterInfo = 31;
        static final int TRANSACTION_setHeartbeatCheckType = 11;
        static final int TRANSACTION_setHeartbeatFixedContent = 8;
        static final int TRANSACTION_setHeartbeatSequence = 10;
        static final int TRANSACTION_setHeartbeatSocketHoldTime = 9;
        static final int TRANSACTION_setHeartbeatTimeout = 12;
        static final int TRANSACTION_setImapIdCmd = 13;
        static final int TRANSACTION_setNrtTime = 22;
        static final int TRANSACTION_setReconnEnable = 21;
        static final int TRANSACTION_startBastetProxy = 5;
        static final int TRANSACTION_stopBastetProxy = 6;
        static final int TRANSACTION_updateEmailBoxInfo = 17;
        static final int TRANSACTION_updateExchangeWebXmlInfo = 18;
        static final int TRANSACTION_updateHeartbeatFileDescriptor = 16;
        static final int TRANSACTION_updateRepeatInterval = 19;
        static final int TRANSACTION_updateServerInfo = 20;

        private static class Proxy implements IBastetManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public boolean isProxyServiceAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isProxyProtocolSupport(int protocol) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(protocol);
                    boolean z = false;
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int initHeartbeatProxy(int fd, int intervalType, IBastetListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(fd);
                    _data.writeInt(intervalType);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int initEmailProxy(int emailType, String addr, int port, int securityMode, int intervalType, String account, String pwd, IBastetListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(emailType);
                    _data.writeString(addr);
                    _data.writeInt(port);
                    _data.writeInt(securityMode);
                    _data.writeInt(intervalType);
                    _data.writeString(account);
                    _data.writeString(pwd);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int startBastetProxy(int proxyId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(proxyId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int stopBastetProxy(int proxyId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(proxyId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int clearProxyById(int proxyId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(proxyId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setHeartbeatFixedContent(int proxyId, byte[] send, byte[] reply) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(proxyId);
                    _data.writeByteArray(send);
                    _data.writeByteArray(reply);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setHeartbeatSocketHoldTime(int proxyId, long timeMillis) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(proxyId);
                    _data.writeLong(timeMillis);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setHeartbeatSequence(int proxyId, int seqStart, int seqStep, int seqMin, int seqMax) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(proxyId);
                    _data.writeInt(seqStart);
                    _data.writeInt(seqStep);
                    _data.writeInt(seqMin);
                    _data.writeInt(seqMax);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setHeartbeatCheckType(int proxyId, int checkType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(proxyId);
                    _data.writeInt(checkType);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setHeartbeatTimeout(int proxyId, long timeoutMillis, int maxSendCount) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(proxyId);
                    _data.writeLong(timeoutMillis);
                    _data.writeInt(maxSendCount);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setImapIdCmd(int proxyId, String idCmd) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(proxyId);
                    _data.writeString(idCmd);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setExchangeHttpHeader(int proxyId, String httpProtoVer, String userAgent, String encoding, String policyKey, String hostName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(proxyId);
                    _data.writeString(httpProtoVer);
                    _data.writeString(userAgent);
                    _data.writeString(encoding);
                    _data.writeString(policyKey);
                    _data.writeString(hostName);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setDeviceId(int proxyId, String deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(proxyId);
                    _data.writeString(deviceId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int updateHeartbeatFileDescriptor(int proxyId, int fd) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(proxyId);
                    _data.writeInt(fd);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int updateEmailBoxInfo(int proxyId, String folderName, String boxLatestUid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(proxyId);
                    _data.writeString(folderName);
                    _data.writeString(boxLatestUid);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int updateExchangeWebXmlInfo(int proxyId, String collectionId, String syncKey, int syncType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(proxyId);
                    _data.writeString(collectionId);
                    _data.writeString(syncKey);
                    _data.writeInt(syncType);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int updateRepeatInterval(int proxyId, int intervalType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(proxyId);
                    _data.writeInt(intervalType);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int updateServerInfo(int proxyId, String addr, int port, int securityMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(proxyId);
                    _data.writeString(addr);
                    _data.writeInt(port);
                    _data.writeInt(securityMode);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setReconnEnable(int proxyId, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(proxyId);
                    _data.writeInt(enable);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setNrtTime(int proxyId, long timeout) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(proxyId);
                    _data.writeLong(timeout);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int notifyNrtTimeout() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int inquireNetworkQuality() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int prepareHeartbeatProxy(int fd, IBastetListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(fd);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int initHwBastetService(IBastetListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void broadcastReceived(int action) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(action);
                    this.mRemote.transact(27, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void packageChangedReceived(int action, String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(action);
                    _data.writeString(name);
                    this.mRemote.transact(28, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public int hrtAppActivity(int activity, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(activity);
                    _data.writeInt(uid);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int indicateAction(int value, int action, int reserve) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(value);
                    _data.writeInt(action);
                    _data.writeInt(reserve);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setFilterInfo(int action, int pid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(action);
                    _data.writeInt(pid);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int configAppUidList(int[] uidList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(uidList);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int notifyAlarmTimeout(int operation) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(operation);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int configDataAccelerate(int uid, int usAppState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(usAppState);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBastetManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IBastetManager)) {
                return new Proxy(obj);
            }
            return (IBastetManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int i = code;
            Parcel parcel = data;
            Parcel parcel2 = reply;
            String descriptor = DESCRIPTOR;
            if (i != 1598968902) {
                int _result;
                int _result2;
                int _result3;
                int _result4;
                int _result5;
                switch (i) {
                    case 1:
                        parcel.enforceInterface(descriptor);
                        boolean _result6 = isProxyServiceAvailable();
                        reply.writeNoException();
                        parcel2.writeInt(_result6);
                        return true;
                    case 2:
                        parcel.enforceInterface(descriptor);
                        boolean _result7 = isProxyProtocolSupport(data.readInt());
                        reply.writeNoException();
                        parcel2.writeInt(_result7);
                        return true;
                    case 3:
                        parcel.enforceInterface(descriptor);
                        _result = initHeartbeatProxy(data.readInt(), data.readInt(), com.huawei.android.bastet.IBastetListener.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        parcel2.writeInt(_result);
                        return true;
                    case 4:
                        parcel.enforceInterface(descriptor);
                        _result2 = initEmailProxy(data.readInt(), data.readString(), data.readInt(), data.readInt(), data.readInt(), data.readString(), data.readString(), com.huawei.android.bastet.IBastetListener.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        parcel2.writeInt(_result2);
                        return true;
                    case 5:
                        parcel.enforceInterface(descriptor);
                        _result3 = startBastetProxy(data.readInt());
                        reply.writeNoException();
                        parcel2.writeInt(_result3);
                        return true;
                    case 6:
                        parcel.enforceInterface(descriptor);
                        _result3 = stopBastetProxy(data.readInt());
                        reply.writeNoException();
                        parcel2.writeInt(_result3);
                        return true;
                    case 7:
                        parcel.enforceInterface(descriptor);
                        _result3 = clearProxyById(data.readInt());
                        reply.writeNoException();
                        parcel2.writeInt(_result3);
                        return true;
                    case 8:
                        parcel.enforceInterface(descriptor);
                        _result = setHeartbeatFixedContent(data.readInt(), data.createByteArray(), data.createByteArray());
                        reply.writeNoException();
                        parcel2.writeInt(_result);
                        return true;
                    case 9:
                        parcel.enforceInterface(descriptor);
                        _result = setHeartbeatSocketHoldTime(data.readInt(), data.readLong());
                        reply.writeNoException();
                        parcel2.writeInt(_result);
                        return true;
                    case 10:
                        parcel.enforceInterface(descriptor);
                        _result2 = setHeartbeatSequence(data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        parcel2.writeInt(_result2);
                        return true;
                    case 11:
                        parcel.enforceInterface(descriptor);
                        _result4 = setHeartbeatCheckType(data.readInt(), data.readInt());
                        reply.writeNoException();
                        parcel2.writeInt(_result4);
                        return true;
                    case 12:
                        parcel.enforceInterface(descriptor);
                        _result5 = setHeartbeatTimeout(data.readInt(), data.readLong(), data.readInt());
                        reply.writeNoException();
                        parcel2.writeInt(_result5);
                        return true;
                    case 13:
                        parcel.enforceInterface(descriptor);
                        _result4 = setImapIdCmd(data.readInt(), data.readString());
                        reply.writeNoException();
                        parcel2.writeInt(_result4);
                        return true;
                    case 14:
                        parcel.enforceInterface(descriptor);
                        _result2 = setExchangeHttpHeader(data.readInt(), data.readString(), data.readString(), data.readString(), data.readString(), data.readString());
                        reply.writeNoException();
                        parcel2.writeInt(_result2);
                        return true;
                    case 15:
                        parcel.enforceInterface(descriptor);
                        _result4 = setDeviceId(data.readInt(), data.readString());
                        reply.writeNoException();
                        parcel2.writeInt(_result4);
                        return true;
                    case 16:
                        parcel.enforceInterface(descriptor);
                        _result4 = updateHeartbeatFileDescriptor(data.readInt(), data.readInt());
                        reply.writeNoException();
                        parcel2.writeInt(_result4);
                        return true;
                    case 17:
                        parcel.enforceInterface(descriptor);
                        _result = updateEmailBoxInfo(data.readInt(), data.readString(), data.readString());
                        reply.writeNoException();
                        parcel2.writeInt(_result);
                        return true;
                    case 18:
                        parcel.enforceInterface(descriptor);
                        _result5 = updateExchangeWebXmlInfo(data.readInt(), data.readString(), data.readString(), data.readInt());
                        reply.writeNoException();
                        parcel2.writeInt(_result5);
                        return true;
                    case 19:
                        parcel.enforceInterface(descriptor);
                        _result4 = updateRepeatInterval(data.readInt(), data.readInt());
                        reply.writeNoException();
                        parcel2.writeInt(_result4);
                        return true;
                    case 20:
                        parcel.enforceInterface(descriptor);
                        _result5 = updateServerInfo(data.readInt(), data.readString(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        parcel2.writeInt(_result5);
                        return true;
                    case 21:
                        parcel.enforceInterface(descriptor);
                        _result4 = setReconnEnable(data.readInt(), data.readInt() != 0);
                        reply.writeNoException();
                        parcel2.writeInt(_result4);
                        return true;
                    case 22:
                        parcel.enforceInterface(descriptor);
                        _result = setNrtTime(data.readInt(), data.readLong());
                        reply.writeNoException();
                        parcel2.writeInt(_result);
                        return true;
                    case 23:
                        parcel.enforceInterface(descriptor);
                        _result2 = notifyNrtTimeout();
                        reply.writeNoException();
                        parcel2.writeInt(_result2);
                        return true;
                    case 24:
                        parcel.enforceInterface(descriptor);
                        _result2 = inquireNetworkQuality();
                        reply.writeNoException();
                        parcel2.writeInt(_result2);
                        return true;
                    case 25:
                        parcel.enforceInterface(descriptor);
                        _result4 = prepareHeartbeatProxy(data.readInt(), com.huawei.android.bastet.IBastetListener.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        parcel2.writeInt(_result4);
                        return true;
                    case 26:
                        parcel.enforceInterface(descriptor);
                        _result3 = initHwBastetService(com.huawei.android.bastet.IBastetListener.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        parcel2.writeInt(_result3);
                        return true;
                    case 27:
                        parcel.enforceInterface(descriptor);
                        broadcastReceived(data.readInt());
                        return true;
                    case 28:
                        parcel.enforceInterface(descriptor);
                        packageChangedReceived(data.readInt(), data.readString());
                        return true;
                    case 29:
                        parcel.enforceInterface(descriptor);
                        _result4 = hrtAppActivity(data.readInt(), data.readInt());
                        reply.writeNoException();
                        parcel2.writeInt(_result4);
                        return true;
                    case 30:
                        parcel.enforceInterface(descriptor);
                        _result = indicateAction(data.readInt(), data.readInt(), data.readInt());
                        reply.writeNoException();
                        parcel2.writeInt(_result);
                        return true;
                    case 31:
                        parcel.enforceInterface(descriptor);
                        _result4 = setFilterInfo(data.readInt(), data.readInt());
                        reply.writeNoException();
                        parcel2.writeInt(_result4);
                        return true;
                    case 32:
                        parcel.enforceInterface(descriptor);
                        _result3 = configAppUidList(data.createIntArray());
                        reply.writeNoException();
                        parcel2.writeInt(_result3);
                        return true;
                    case 33:
                        parcel.enforceInterface(descriptor);
                        _result3 = notifyAlarmTimeout(data.readInt());
                        reply.writeNoException();
                        parcel2.writeInt(_result3);
                        return true;
                    case 34:
                        parcel.enforceInterface(descriptor);
                        _result4 = configDataAccelerate(data.readInt(), data.readInt());
                        reply.writeNoException();
                        parcel2.writeInt(_result4);
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            }
            parcel2.writeString(descriptor);
            return true;
        }
    }

    void broadcastReceived(int i) throws RemoteException;

    int clearProxyById(int i) throws RemoteException;

    int configAppUidList(int[] iArr) throws RemoteException;

    int configDataAccelerate(int i, int i2) throws RemoteException;

    int hrtAppActivity(int i, int i2) throws RemoteException;

    int indicateAction(int i, int i2, int i3) throws RemoteException;

    int initEmailProxy(int i, String str, int i2, int i3, int i4, String str2, String str3, IBastetListener iBastetListener) throws RemoteException;

    int initHeartbeatProxy(int i, int i2, IBastetListener iBastetListener) throws RemoteException;

    int initHwBastetService(IBastetListener iBastetListener) throws RemoteException;

    int inquireNetworkQuality() throws RemoteException;

    boolean isProxyProtocolSupport(int i) throws RemoteException;

    boolean isProxyServiceAvailable() throws RemoteException;

    int notifyAlarmTimeout(int i) throws RemoteException;

    int notifyNrtTimeout() throws RemoteException;

    void packageChangedReceived(int i, String str) throws RemoteException;

    int prepareHeartbeatProxy(int i, IBastetListener iBastetListener) throws RemoteException;

    int setDeviceId(int i, String str) throws RemoteException;

    int setExchangeHttpHeader(int i, String str, String str2, String str3, String str4, String str5) throws RemoteException;

    int setFilterInfo(int i, int i2) throws RemoteException;

    int setHeartbeatCheckType(int i, int i2) throws RemoteException;

    int setHeartbeatFixedContent(int i, byte[] bArr, byte[] bArr2) throws RemoteException;

    int setHeartbeatSequence(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    int setHeartbeatSocketHoldTime(int i, long j) throws RemoteException;

    int setHeartbeatTimeout(int i, long j, int i2) throws RemoteException;

    int setImapIdCmd(int i, String str) throws RemoteException;

    int setNrtTime(int i, long j) throws RemoteException;

    int setReconnEnable(int i, boolean z) throws RemoteException;

    int startBastetProxy(int i) throws RemoteException;

    int stopBastetProxy(int i) throws RemoteException;

    int updateEmailBoxInfo(int i, String str, String str2) throws RemoteException;

    int updateExchangeWebXmlInfo(int i, String str, String str2, int i2) throws RemoteException;

    int updateHeartbeatFileDescriptor(int i, int i2) throws RemoteException;

    int updateRepeatInterval(int i, int i2) throws RemoteException;

    int updateServerInfo(int i, String str, int i2, int i3) throws RemoteException;
}
