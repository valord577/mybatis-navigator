#!/usr/bin/env python3

"""

"""

# fmt: off

import os
import shutil
import subprocess as sp
import sys


APP_HOME = os.path.abspath(os.path.dirname(__file__))

DEFAULT_JVM_OPTS = [
    '-Xms64m', '-Xmx64m', '-Xint', '-Dfile.encoding=UTF-8',
]

BUILD_ENV = os.environ.copy(); JAVACMD = None
if java_home := BUILD_ENV.get('JAVA_HOME'):
    JAVACMD = f'{java_home}/bin/java'
if not JAVACMD:
    JAVACMD = shutil.which('java')
if not JAVACMD:
    raise FileNotFoundError('No executable found: java')


args = [ JAVACMD, *DEFAULT_JVM_OPTS,
    f'-Dorg.gradle.appname={os.path.basename(__file__)}',
    '-classpath', f'{APP_HOME}/gradle/wrapper/gradle-wrapper.jar',
    'org.gradle.wrapper.GradleWrapperMain', *sys.argv[1:],
]
sys.exit((sp.run(args=args, env=BUILD_ENV, shell=False)).returncode)
