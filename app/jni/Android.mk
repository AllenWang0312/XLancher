LOCAL_PATH:=$(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE:= spi_transfer
LOCAL_SRC_FILES:=spi_transfer.c
include $(BUILD_SHARED_LIBRARY)
